import grpc
import pingpong_pb2
import pingpong_pb2_grpc

def run():
    # Connect to the gRPC server
    with grpc.insecure_channel('localhost:8080') as channel:
        stub = pingpong_pb2_grpc.PingPongServiceStub(channel)

        # Take input from the user
        message = input("Enter your ping message: ")
        print(f"Client sent message: {message}")

        # Build and send the request
        request = pingpong_pb2.PingPongRequest(message=message)
        response = stub.getPong(request)

        # Output the server's response
        print(f"Client received message: {response.message}")

if __name__ == '__main__':
    run()