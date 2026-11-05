package com.controleco.app.capture

internal fun shouldInspectNotification(
    packageName: String,
    ownPackageName: String,
    watchedPackages: Set<String>,
    debugCaptureAll: Boolean,
): Boolean = packageName != ownPackageName &&
    (packageName in watchedPackages || debugCaptureAll)

/** 系统重绑后只能补读仍在通知栏中的近期通知；旧通知不应突然变成新账。 */
internal fun shouldReplayNotification(systemPostTime: Long, now: Long): Boolean =
    systemPostTime > 0L && systemPostTime <= now && now - systemPostTime <= 15 * 60_000L
