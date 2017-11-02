package co.vsco.droidcon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.helloworldexample.GreeterGrpc;
import io.grpc.helloworldexample.Helloworld;
import io.grpc.stub.StreamObserver;

public class MainActivity extends AppCompatActivity {
    protected ManagedChannel channel;
    protected GreeterGrpc.GreeterStub stub;
    private static final int PORT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
            channel = ManagedChannelBuilder.forAddress("10.10.41.212", 50051).usePlaintext(true).build();
            stub = GreeterGrpc.newStub(channel);
        } catch (IllegalArgumentException e) {}

        Helloworld.HelloRequest request = Helloworld.HelloRequest.newBuilder().setName("test").build();
        stub.sayHello(request, new StreamObserver<Helloworld.HelloReply>() {
            @Override
            public void onNext(Helloworld.HelloReply value) {
                Log.i("MainActivity", "Message: " + value.getMessage());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });

    }
}
