package com.belladati.iot.collector.receiver.endpoint.audioml;

public interface AudioProcessor {

    /**
     * Processes raw stream data to produce avrage values per frequency a maximum values per frequency. By default,
     * the processing is the Fast Fourier transformation. By implementing this interface and a custom tailored
     * transformation can be done.
     *
     * @param rawData raw stream bytes
     * @param fftPoints configured FFT points from the console configuration
     * @return wrapper for averages and maximums
     */
    AvgMax processStreamData(double[] rawData, int fftPoints);

    class AvgMax {
        Double[] avgs;
        Double[] maxs;

        public AvgMax(Double[] avgs, Double[] maxs) {
            this.avgs = avgs;
            this.maxs = maxs;
        }

        public Double[] getAvgs() {
            return avgs;
        }

        public Double[] getMaxs() {
            return maxs;
        }
    }
}
