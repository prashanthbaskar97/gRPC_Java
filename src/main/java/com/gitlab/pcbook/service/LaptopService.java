package com.gitlab.pcbook.service;

import com.gitlab.techschool.pcbook.pb.CreateLaptopRequest;
import com.gitlab.techschool.pcbook.pb.CreateLaptopResponse;
import com.gitlab.techschool.pcbook.pb.Laptop;
import com.gitlab.techschool.pcbook.pb.LaptopServiceGrpc;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.grpc.xds.shaded.io.envoyproxy.envoy.config.accesslog.v3.GrpcStatusFilter;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.UUID;

public class LaptopService extends LaptopServiceGrpc.LaptopServiceImplBase{
    private static final Logger logger=Logger.getLogger(LaptopService.class.getName());
    private LaptopStore store;

    public LaptopService(LaptopStore store){
        this.store=store;
    }

    @Override
    public void createLaptop(CreateLaptopRequest request, StreamObserver<CreateLaptopResponse> responseObserver){
        Laptop laptop=request.getLaptop();

        String id= laptop.getId();logger.info("gota a create-laptop request with ID :" +id);

        UUID uuid;
        if(id.isEmpty()){
            uuid=UUID.randomUUID();
        }else{
            try{
                uuid=UUID.fromString(id);
            }catch (IllegalArgumentException e){
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription(e.getMessage())
                        .asRuntimeException()
                );

                return;
            }
        }


        //heavy processing so add time
//        try{
//            TimeUnit.SECONDS.sleep(6);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }

        if(Context.current().isCancelled()){
            logger.info("request is cancelled");
            responseObserver.onError(
                    Status.CANCELLED
                            .withDescription("request is cancelled")
                            .asRuntimeException()
            );
            return;
        }
        //Save other laptop to the in-memory store
        Laptop other=laptop.toBuilder().setId(uuid.toString()).build();

        try{
            store.Save(other);

        } catch (AlreadyExistsException e) {
            responseObserver.onError(
                    Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
            return;
        }catch (Exception e){
            responseObserver.onError(Status.INTERNAL
                    .withDescription(e.getMessage())
                    .asRuntimeException());
            return;
        }

        CreateLaptopResponse response= CreateLaptopResponse.newBuilder().setId(other.getId()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        logger.info("Saved laptop with ID:"+other.getId());

    }

}
