package com.controleco.app.capture

internal enum class ChannelHealth { NOT_GRANTED, DISCONNECTED, CONNECTED }

internal fun channelHealth(granted: Boolean, connected: Boolean): ChannelHealth = when {
    !granted -> ChannelHealth.NOT_GRANTED
    connected -> ChannelHealth.CONNECTED
    else -> ChannelHealth.DISCONNECTED
}

/** 前台检查时请求系统重新绑定；短时间反复进入页面不能连续打扰系统服务。 */
internal fun shouldRequestListenerRebind(
    granted: Boolean,
    connected: Boolean,
    nowElapsedMs: Long,
    lastAttemptElapsedMs: Long?,
): Boolean = granted && !connected &&
    (lastAttemptElapsedMs == null || nowElapsedMs < lastAttemptElapsedMs ||
        nowElapsedMs - lastAttemptElapsedMs >= 30_000L)
