package com.sairanadheer.bharatagriassignment.repo;

public class NetworkState {
    static final NetworkState LOADED;
    static final NetworkState LOADING;
    private final Status status;
    private final String message;
    static {
        LOADED = new NetworkState(Status.SUCCESS, "Success");
        LOADING = new NetworkState(Status.RUNNING, "Running");
    }

    public NetworkState(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}
