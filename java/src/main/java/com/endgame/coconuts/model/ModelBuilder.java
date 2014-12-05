package com.endgame.coconuts.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ModelBuilder {

    private JetStream jetStream;
    private int energyConstant;

    public ModelBuilder(Reader input) throws IOException {
        parse(input);
    }

    public JetStream getJetStream() {
        return jetStream;
    }

    public int getEnergyConstant() {
        return energyConstant;
    }

    protected void parse(Reader input) throws IOException {
        jetStream = new JetStream();

        BufferedReader reader = new BufferedReader(input);

        String line = reader.readLine();

        energyConstant = Integer.parseInt(line.trim());

        while ((line = reader.readLine()) != null) {
            String [] parts = line.trim().split(" ");

            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());
            int energyRequired = Integer.parseInt(parts[2].trim());

            jetStream.addSegment(start, end, energyRequired);
        }
    }


}
