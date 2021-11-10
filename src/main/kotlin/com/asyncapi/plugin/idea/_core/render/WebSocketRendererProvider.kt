package com.asyncapi.plugin.idea._core.render

import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import org.jetbrains.builtInWebServer.liveReload.WebServerPageConnectionService
import java.lang.reflect.Method

/**
 * @author Pavel Bodiachevskii
 * @since 1.2.0
 */
@Service
class WebSocketRendererProvider {

    private val webServerPageConnectionService = service<WebServerPageConnectionService>()

    fun provide(fullHttpRequest: io.netty.handler.codec.http.FullHttpRequest,
                onlyIfHtmlFile: Boolean = false,
                supplier: java.util.function.Supplier<out com.intellij.openapi.vfs.VirtualFile?>
    ): CharSequence? {
        return try {
            when (ApplicationInfo.getInstance().fullVersion) {
                "2021.1",
                "2021.1.1",
                "2021.1.2",
                "2021.1.3",
                "2021.2",
                "2021.2.1" -> {
                    before_2021_2_2().invoke(webServerPageConnectionService, fullHttpRequest, supplier) as CharSequence?
                }
                else -> {
                    after_2021_2_2().invoke(webServerPageConnectionService, fullHttpRequest, onlyIfHtmlFile, supplier) as CharSequence?
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    @Throws(Exception::class)
    fun before_2021_2_2(): Method {
        return WebServerPageConnectionService::class.java.getMethod(
            "fileRequested",
            io.netty.handler.codec.http.FullHttpRequest::class.java,
            java.util.function.Supplier::class.java
        )
    }

    @Throws(Exception::class)
    fun after_2021_2_2(): Method {
        return WebServerPageConnectionService::class.java.getMethod(
            "fileRequested",
            io.netty.handler.codec.http.FullHttpRequest::class.java,
            Boolean::class.java,
            java.util.function.Supplier::class.java
        )
    }

}