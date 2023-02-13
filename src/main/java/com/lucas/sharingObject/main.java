package com.lucas.sharingObject;

import java.util.Arrays;

public class main {
    public static void main(String args[]) throws InterruptedException {
        UnsafeStates unsafeStates = new UnsafeStates();
        FinalUnsafeStates finalUnsafeStates = new FinalUnsafeStates();
        SafeStates states = new SafeStates();

        System.out.println("unsafeStates:" + "\n" + Arrays.toString(unsafeStates.getStates()));
        new Thread(() -> {
            String[] newStates = unsafeStates.getStates();
            newStates[0] = "UP";
        }).start();
        Thread.sleep(1000);
        System.out.println(Arrays.toString(unsafeStates.getStates()));

        System.out.println("finalUnsafeStates:" + "\n" + Arrays.toString(finalUnsafeStates.getStates()));
        new Thread(() -> {
            String[] newStates = finalUnsafeStates.getStates();
            newStates[0] = "UP";
        }).start();
        Thread.sleep(1000);
        System.out.println(Arrays.toString(finalUnsafeStates.getStates()));

        System.out.println("states:" + "\n" + Arrays.toString(states.getStates()));
        new Thread(() -> {
            String[] newStates = states.getStates();
            newStates[0] = "UP";
        }).start();
        Thread.sleep(1000);
        System.out.println(Arrays.toString(states.getStates()));

    }
}

interface States{
    public String[] getStates();
}

class UnsafeStates implements States {

    private String[] states = new String[] {
            "AK", "AL", "BK", "BL", "CK", "CL"
    };

    public String[] getStates() {
        return states;
    }

}

class FinalUnsafeStates implements States{
    private final String[] states = new String[] {
            "AK", "AL", "BK", "BL", "CK", "CL"
    };

    @Override
    public String[] getStates() {
        return states;
    }
}

class SafeStates implements States {
    private final String[] states = new String[] {
            "AK", "AL", "BK", "BL", "CK", "CL"
    };

    @Override
    public String[] getStates() {
        return Arrays.copyOf(states, states.length);
    }
}

