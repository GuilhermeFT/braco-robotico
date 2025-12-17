package br.gftapps.arduinocontrolpro

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import java.io.DataOutputStream
import java.io.IOException
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress


class SocketConnection : AsyncTask<String, Void, Boolean>() {
    var listener: MyInterface? = null

    fun Parameters(mListener: MyInterface) {
        listener = mListener
    }

    lateinit var address: InetSocketAddress
    lateinit var socket: Socket
    var ip = "192.168.0.255"
    var port = "2400"
    override fun doInBackground(vararg p0: String?): Boolean {

        address = InetSocketAddress(ip, port.toInt())
        socket = Socket()
        return try {
            socket.connect(address, 1000)
            true
        } catch (e: Exception) {
            false

        }
    }

    fun sendMessage(message: String) {
        try {
            val pw = PrintWriter(socket.getOutputStream())
            if (message != null) {
                pw.println(message)
                pw.close()
                socket.close()
            }
        }catch (e: IOException) {

        }
    }

    override fun onPostExecute(result: Boolean?) {
        if (result != null) {
            listener?.VerifySocket(result)
        }
    }
}

interface MyInterface {
    fun VerifySocket(b: Boolean)
}

