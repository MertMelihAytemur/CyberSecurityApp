package tr.com.mermela.cybersecurityfinalproject.domain

/**
 *Created by Mert Melih Aytemur on 5/12/2024.
 */
data class TargetInfo(
    val username: String,
    val attackResult: String?,
    val attackStatus: String?,
    val isActive: Boolean?
)
