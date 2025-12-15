package com.example.tarea_1.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tarea_1.R
import com.example.tarea_1.databinding.FragmentContactBinding
import android.widget.VideoView
class ContactFragment : Fragment() {
    private lateinit var binding : FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+34123456789")
            startActivity(intent)
        }


        binding.btnWhatsApp.setOnClickListener {
            val url = "https://wa.me/34123456789"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }


        binding.btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:info@lalibreria.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta desde la app")
            startActivity(intent)
        }
        val videoPath = "android.resource://" + requireContext().packageName + "/" + R.raw.video
        val uri = Uri.parse(videoPath)
        binding.videoView.setVideoURI(uri)
        binding.videoView.setMediaController(android.widget.MediaController(context))
        binding.videoView.start()
        binding.videoView.setOnCompletionListener {
            binding.videoView.start()
        }
    }
}