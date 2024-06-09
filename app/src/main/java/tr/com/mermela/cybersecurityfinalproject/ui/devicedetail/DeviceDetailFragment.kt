package tr.com.mermela.cybersecurityfinalproject.ui.devicedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import tr.com.mermela.cybersecurityfinalproject.databinding.FragmentDeviceDetailFagmentBinding
import tr.com.mermela.cybersecurityfinalproject.domain.AttackResult
import tr.com.mermela.cybersecurityfinalproject.domain.DiskInfo
import tr.com.mermela.cybersecurityfinalproject.domain.NetworkInfo
import tr.com.mermela.cybersecurityfinalproject.domain.OsInfo
import tr.com.mermela.cybersecurityfinalproject.domain.SystemInfo
import tr.com.mermela.cybersecurityfinalproject.domain.TargetInfo
import tr.com.mermela.cybersecurityfinalproject.ui.devicedetail.adapter.AttackResultsAdapter
import tr.com.mermela.cybersecurityfinalproject.ui.devicedetail.adapter.GridSpacingItemDecoration
import tr.com.mermela.cybersecurityfinalproject.ui.devicelist.adapter.DeviceListAdapter

class DeviceDetailFragment : Fragment() {

    private lateinit var binding : FragmentDeviceDetailFagmentBinding

    private val viewModel : DeviceDetailViewModel by viewModels()

    private lateinit var adapter : AttackResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailFagmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val targetInfo = arguments?.getParcelable<TargetInfo>("targetInfo")

        targetInfo?.let {
            listenForDetails(it.username)
            binding.tvUsername.text = it.username
        }
    }

    private fun listenForDetails(username: String) {
        binding.progressBar.isVisible = true
        val userRef = FirebaseDatabase.getInstance().getReference("client/$username/attack/attack_result")
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val attackResults = mutableListOf<AttackResult>()
                snapshot.children.forEach { resultSnapshot ->
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
                updateRecyclerView(attackResults)
                binding.progressBar.isVisible = false
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load data: ${error.toException()}", Toast.LENGTH_LONG).show()
                binding.progressBar.isVisible = false
            }
        })
    }

    private fun setupRecyclerView(usersList: MutableList<AttackResult>) {
        // Setup GridLayoutManager with 2 columns
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvResults.layoutManager = layoutManager
        binding.rvResults.addItemDecoration(GridSpacingItemDecoration(2, 10, true))
        updateRecyclerView(usersList)  // Call with empty list initially or as required
    }

    private fun updateRecyclerView(usersList: MutableList<AttackResult>) {
        adapter = AttackResultsAdapter(usersList)
        binding.rvResults.adapter = adapter
    }
}