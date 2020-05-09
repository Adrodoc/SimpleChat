package com.example.simplechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_17
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = MyWebSocketClient(URI("ws://10.0.2.2:8080"), Draft_17())
        client.connect()
        send.setOnClickListener {
            client.send("${username.text}: ${chatmessage.text}")
            chatmessage.setText("")
        }
    }

    inner class MyWebSocketClient(uri: URI, draft: Draft) : WebSocketClient(uri, draft) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            println("onOpen($uri, $draft)")
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            println("onClose($code, $reason, $remote)")
        }

        override fun onMessage(message: String?) {
            this@MainActivity.chattext.text = "$message\n${this@MainActivity.chattext.text}";
        }

        override fun onError(ex: Exception?) {
            println("onError($ex)")
        }
    }
}
