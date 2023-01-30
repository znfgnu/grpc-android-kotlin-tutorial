package tech.sikorski.simplegrpcclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.runBlocking
import tech.sikorski.pingpong.PingPongGrpcKt
import tech.sikorski.pingpong.Pingpong
import tech.sikorski.simplegrpcclient.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val port = 50051
        val channel = ManagedChannelBuilder.forAddress("192.168.0.13", port).usePlaintext().build()
        val stub = PingPongGrpcKt.PingPongCoroutineStub(channel)

        binding.buttonFirst.setOnClickListener {
            runBlocking {
                val request = Pingpong.PingPongMsg.newBuilder().setPayload("Sikorski").build()
                val response = stub.ping(request)
                Log.i("result", response.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}