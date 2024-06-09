package tr.com.mermela.cybersecurityfinalproject.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *Created by Mert Melih Aytemur on 5/12/2024.
 */
@Parcelize
data class TargetInfo(
    val username: String = "",
    val attackResults: List<AttackResult> = emptyList()
) : Parcelable

@Parcelize
data class AttackResult(
    val timestamp: String = "",
    val diskInfo: DiskInfo = DiskInfo(),
    val networkInfo: List<NetworkInfo> = emptyList(),
    val osInfo: OsInfo = OsInfo(),
    val systemInfo: SystemInfo = SystemInfo()
) : Parcelable

@Parcelize
data class DiskInfo(
    val Model: String = "",
    val Size: Long = 0L
) : Parcelable

@Parcelize
data class NetworkInfo(
    val Description: String = "",
    val IPAddress: String = "",
    val MACAddress: String = ""
) : Parcelable

@Parcelize
data class OsInfo(
    val Caption: String = "",
    val Version: String = ""
) : Parcelable

@Parcelize
data class SystemInfo(
    val Manufacturer: String = "",
    val Model: String = "",
    val NumberOfProcessors: Int = 0,
    val TotalPhysicalMemory: Long = 0L
) : Parcelable