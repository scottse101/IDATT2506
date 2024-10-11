package com.example.leksjon_6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var sentMessagesTextView: TextView
    private lateinit var receivedMessagesTextView: TextView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)
        sentMessagesTextView = findViewById(R.id.textViewDisplaySentMessages)
        receivedMessagesTextView = findViewById(R.id.textViewDisplayReceivedMessages)

        Server(receivedMessagesTextView).start()

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    Client(sentMessagesTextView).sendMessage(message)
                }
                editTextMessage.text.clear()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            Client(sentMessagesTextView).start()
        }
    }
}

class Server(private val receivedTextView: TextView, private val PORT: Int = 12345) {
    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ServerSocket(PORT).use { serverSocket ->
                    while (true) {
                        serverSocket.accept().use { clientSocket ->
                            readFromClient(clientSocket)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun readFromClient(socket: Socket) {
        try {
            val reader = socket.getInputStream().bufferedReader()
            val message = reader.readLine()
            updateReceivedUI("Mottatt melding fra Klient: $message")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateReceivedUI(message: String) {
        MainScope().launch {
            receivedTextView.append("\n$message")
        }
    }
}


class Client(private val sentTextView: TextView, private val SERVER_IP: String = "10.0.2.2", private val SERVER_PORT: Int = 12345) {
    private var listeningSocket: Socket? = null

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listeningSocket = Socket(SERVER_IP, SERVER_PORT)
                updateSentUI("Koblet til serveren")

                listenForMessages(listeningSocket!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Socket(SERVER_IP, SERVER_PORT).use { socket ->
                    val writer = socket.getOutputStream().bufferedWriter()
                    writer.write(message + "\n")
                    writer.flush()
                    updateSentUI("Sendt melding: $message")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun listenForMessages(socket: Socket) {
        try {
            val reader = socket.getInputStream().bufferedReader()
            var message: String?

            while (true) {
                message = reader.readLine() ?: break
                updateReceivedUI("Melding fra serveren: $message")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateSentUI(message: String) {
        MainScope().launch {
            sentTextView.append("\n$message")
        }
    }

    private fun updateReceivedUI(message: String) {
        MainScope().launch {
            val receivedMessagesTextView = sentTextView.rootView.findViewById<TextView>(R.id.textViewDisplayReceivedMessages)
            receivedMessagesTextView.append("\n$message")
        }
    }
}




