package com.example.zierules.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.zierules.LoginActivity
import com.example.zierules.MyApplication
import com.example.zierules.R

import com.example.zierules.databinding.FragmentProfileBinding
import com.example.zierules.helper.Constant
import com.example.zierules.helper.PreferenceHelper
import com.example.zierules.json.StudentData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*
import kotlin.collections.HashMap

class ProfileFragment : Fragment() {

    lateinit var sharePref: PreferenceHelper
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val dashboardViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharePref = PreferenceHelper(requireContext())

        binding.txtLogout.setOnClickListener{
            MaterialAlertDialogBuilder(requireContext())
                .setView(R.layout.logout_confirmation)
                .setPositiveButton("Ya") {dialog, which ->
                    logout()
                }
                .setNeutralButton("Tidak") {dialog, which ->

                }
                .show()
        }

        return root

    }

    override fun onStart() {
        super.onStart()

        if (!sharePref.getBoolean(Constant.PREF_IS_FETCHED)) {

            if (!isNetworkAvailable(requireContext())) {
                showIntenetDialog()
                return
            }

            getProfile()

        } else {
            if (!isNetworkAvailable(requireContext())) {
                getProfileOffline()
                return
            }
            getProfile()
        }

    }

    private fun getProfile(){
        val queue = Volley.newRequestQueue(requireContext())
        val url = "${MyApplication.BASE_URL}/student/profile"
        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    //PARSE JSON
                    val jsonString = response.toString()
                    val studentData = parseJson(jsonString)

                    //GET DATA FROM JSON
                    val image_profile = studentData.student.image
                    val nis = studentData.student.nis
                    var role = studentData.student.role
                    val nama = studentData.student.name
                    val kelas = studentData.student.`class`
                    val jml_pelanggaran = studentData.student.dataViolations
                    val jml_prestasi = studentData.student.dataAchievements
                    val jml_tugas = studentData.student.dataTasks
                    val code = studentData.student.code
                    val image = "${MyApplication.URL}${image_profile}"
                    role = role.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    val url_image = image.replace("public", "storage")
                    //PUT DATA TO ELEMENT
                    binding.txtNis.text = nis.toString()
                    Glide.with(requireContext())
                        .load(url_image)
                        .into(binding.profilePhoto)
                    binding.txtRole.text = role.toString()
                    binding.txtNamaSiswa.text = nama.toString()
                    binding.txtKelasSiswa.text = kelas.toString()
                    binding.txtPelanggaran.text = jml_pelanggaran.toString()
                    binding.txtPrestasi.text =  jml_prestasi.toString()
                    binding.txtTugas.text = jml_tugas.toString()
                    //Turn NIS into QR Code
                    val qrCodeBitmap = getQrCodeBitmap(code)
                    binding.QRNis.setImageBitmap(qrCodeBitmap)

                    //PUT DATA TO CACHE
                    sharePref.put(Constant.PREF_SISWA_PP, url_image)
                    sharePref.put(Constant.PREF_SISWA_NAMA, nama)
                    sharePref.put(Constant.PREF_SISWA_CODE, code)
                    sharePref.put(Constant.PREF_SISWA_ROLE, role)
                    sharePref.put(Constant.PREF_SISWA_KELAS, kelas)
                    sharePref.put(Constant.PREF_SISWA_PELANGGARAN, jml_pelanggaran.toString())
                    sharePref.put(Constant.PREF_SISWA_PRESTASI, jml_prestasi.toString())
                    sharePref.put(Constant.PREF_SISWA_TUGAS, jml_tugas.toString())

                    sharePref.put(Constant.PREF_IS_FETCHED, true)

                } catch (e: Exception) {
//                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    sharePref.put(Constant.PREF_IS_FETCHED, false)
                }
            },
            { error ->
//                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                sharePref.put(Constant.PREF_IS_FETCHED, false)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${sharePref.getString(Constant.PREF_TOKEN.toString())}"
                return headers
            }
        }
        queue.add(jsonObjectRequest)

    }


    private fun getProfileOffline() {
        binding.txtNis.text = sharePref.getString(Constant.PREF_NIS)
        Glide.with(requireContext())
            .load(sharePref.getString(Constant.PREF_SISWA_PP))
            .into(binding.profilePhoto)
        binding.txtRole.text = sharePref.getString(Constant.PREF_SISWA_ROLE)
        binding.txtNamaSiswa.text = sharePref.getString(Constant.PREF_SISWA_NAMA)
        binding.txtKelasSiswa.text = sharePref.getString(Constant.PREF_SISWA_KELAS)
        binding.txtPelanggaran.text = sharePref.getString(Constant.PREF_SISWA_PELANGGARAN)
        binding.txtPrestasi.text =  sharePref.getString(Constant.PREF_SISWA_PRESTASI)
        binding.txtTugas.text = sharePref.getString(Constant.PREF_SISWA_TUGAS)
        //Turn NIS into QR Code
        val qrCodeBitmap = getQrCodeBitmap(sharePref.getString(Constant.PREF_SISWA_CODE).toString())
        binding.QRNis.setImageBitmap(qrCodeBitmap)
    }


    private fun parseJson(jsonString: String): StudentData {
        val gson = Gson()
        return gson.fromJson(jsonString, StudentData::class.java)
    }

    fun getQrCodeBitmap(text: String): Bitmap {
        val size = 512 //pixels
        val hints = hashMapOf<EncodeHintType, Int>().also { it[EncodeHintType.MARGIN] = 1 } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    private fun logout() {
        sharePref.put(Constant.PREF_NIS, "")
        sharePref.put(Constant.PREF_IS_LOGIN, false)
        sharePref.put(Constant.PREF_PASSWORD, "")
        sharePref.put(Constant.PREF_TOKEN, "")
        sharePref.put(Constant.PREF_SISWA_PP, "")
        sharePref.put(Constant.PREF_SISWA_NAMA, "")
        sharePref.put(Constant.PREF_SISWA_KELAS, "")
        sharePref.put(Constant.PREF_SISWA_ROLE, "")
        sharePref.put(Constant.PREF_SISWA_PELANGGARAN, "")
        sharePref.put(Constant.PREF_SISWA_PRESTASI, "")
        sharePref.put(Constant.PREF_SISWA_TUGAS, "")
        sharePref.put(Constant.PREF_IS_FETCHED, false)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    private fun showIntenetDialog() {
        val builder = AlertDialog.Builder(requireContext())
            .setView(R.layout.check_internet)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}