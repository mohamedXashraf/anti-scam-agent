package tech.mohamedashraf.presentation.agent

import java.io.File

sealed class AgentViewIntent {
    data class StartAgenticCheck(val productId: String, val liveProductVideo: File?) : AgentViewIntent()
}