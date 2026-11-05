package com.controleco.app.capture

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ListenerHealthTest {
    @Test fun `已授权但服务未连接不能显示为已连接`() {
        assertEquals(ChannelHealth.DISCONNECTED, channelHealth(granted = true, connected = false))
        assertEquals(ChannelHealth.CONNECTED, channelHealth(granted = true, connected = true))
        assertEquals(ChannelHealth.NOT_GRANTED, channelHealth(granted = false, connected = true))
    }

    @Test fun `前台发现授权但未连接时请求重绑且限制频率`() {
        assertTrue(shouldRequestListenerRebind(true, false, 50_000L, null))
        assertFalse(shouldRequestListenerRebind(true, false, 60_000L, 50_000L))
        assertTrue(shouldRequestListenerRebind(true, false, 80_000L, 50_000L))
        assertFalse(shouldRequestListenerRebind(false, false, 80_000L, null))
        assertFalse(shouldRequestListenerRebind(true, true, 80_000L, null))
    }
}
