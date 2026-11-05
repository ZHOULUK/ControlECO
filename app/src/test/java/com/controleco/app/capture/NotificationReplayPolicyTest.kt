package com.controleco.app.capture

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NotificationReplayPolicyTest {
    private val now = 1_800_000_000_000L
    private val watched = setOf(NotificationRules.WECHAT, "com.icbc")
    private val ownPackage = "com.zhouluk.controleco"

    @Test fun `只读取用户选择的应用通知`() {
        assertTrue(shouldInspectNotification(NotificationRules.WECHAT, ownPackage, watched, false))
        assertFalse(shouldInspectNotification("com.chat.other", ownPackage, watched, false))
        assertFalse(shouldInspectNotification(ownPackage, ownPackage, watched, false))
    }

    @Test fun `重连只补读近期仍在通知栏的通知`() {
        assertTrue(shouldReplayNotification(now - 60_000L, now))
        assertTrue(shouldReplayNotification(now - 15 * 60_000L, now))
        assertFalse(shouldReplayNotification(now - 15 * 60_000L - 1, now))
        assertFalse(shouldReplayNotification(0L, now))
        assertFalse(shouldReplayNotification(now + 1, now))
    }
}
