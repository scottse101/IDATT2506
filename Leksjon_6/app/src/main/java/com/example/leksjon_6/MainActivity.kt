package com.example.leksjon_6

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.leksjon_6.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val serverPort = 12345
    private var isServer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (isServer) {
            startServer()
        } else {
            connectToServer("10.0.2.2")
        }

        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            sendMessage(message)
        }
    }

    private fun startServer() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val serverSocket = ServerSocket(serverPort, 0, InetAddress.getByName("0.0.0.0"))
                Log.d("Server", "Server started, waiting for clients...")
                val clientSocket = serverSocket.accept()

                val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                PrintWriter(clientSocket.getOutputStream(), true)

                while (true) {
                    val message = input.readLine()
                    Log.d("Server", "Mottatt melding: $message")
                    if (message != null) {
                        withContext(Dispatchers.Main) {
                            binding.textViewReceivedMessages.append("\nMottatt: $message")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun connectToServer(ipAddress: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket(ipAddress, serverPort)
                PrintWriter(socket.getOutputStream(), true)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))

                while (true) {
                    val message = input.readLine()
                    if (message != null) {
                        withContext(Dispatchers.Main) {
                            binding.textViewReceivedMessages.append("\nMottatt: $message")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val socket = Socket("10.0.2.2", serverPort)
                val output = PrintWriter(socket.getOutputStream(), true)

                output.println(message)

                withContext(Dispatchers.Main) {
                    binding.textViewSentMessages.append("\nSendt: $message")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
