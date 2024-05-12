package tr.com.mermela.cybersecurityfinalproject.ui.devicelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tr.com.mermela.cybersecurityfinalproject.databinding.ItemDeviceBinding

/**
 *Created by Mert Melih Aytemur on 4/28/2024.
 */
class DeviceListAdapter(
    private val deviceList : ArrayList<String>,
    private val onDetailButtonClick : () -> Unit
) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(deviceName : String){
            with(binding){
                tvUsername.text = deviceName

                btnDetail.setOnClickListener {
                    onDetailButtonClick.invoke()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDeviceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = deviceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = deviceList[position]
        holder.bind(item)
    }
}