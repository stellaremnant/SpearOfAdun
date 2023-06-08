import java.awt.Color;
import java.time.Duration;

import ihs.apcs.spacebattle.RegistrationData;
import lib.NCli;

public class FindTheMiddle extends NCli.ShipComputer {
    public static void main(final String[] args) throws Exception {
        new NCli("172.30.226.161", new FindTheMiddle());
    }

    private NCli.Vec center;

    private long total;
    private long totalInner;
    private int cycles;

    @Override protected RegistrationData init(final int width, final int height) {
        this.center = new NCli.Vec(width / 2, height / 2);
        return new RegistrationData("Spear of Adun", new Color(57, 216, 255), 10);
    }

    @Override protected void run() {
        while(true) {
            final long start = System.currentTimeMillis();
            //this.glideBoost(this.center, 100, 3, 0.25, 30); // 22.25 avg
            //this.glideBoost(this.center, 100, 2, 0.1, 30); // 20 avg
            //this.glide(this.center, 100, false, 0.1, 30); // 19.5 avg
            this.glideBoost(this.center, 100, 1, 0.25, Duration.ofSeconds(30)); // 19.5 avg
            //this.glideBoost(this.center, 100, 2, 0.35, 30); // 18.75 avg (overshoots but still gets the point the majority of times)
            //this.glideBoost(this.center, 100, 2, 0.25, 30); // 21.1 avg
            this.totalInner += System.currentTimeMillis() - start;
            this.untilChange(() -> this.env.getGameInfo().getScore(), 0.1, Duration.ofSeconds(3));
            this.untilSufficientEnergy(100);
            this.total += System.currentTimeMillis() - start;
            this.cycles++;

            this.status("%d cycles\n%.3fs average cycle time\n%.3fs average time (w/o downtime)\n%.2f downtime percentage".formatted(
                this.cycles,
                (double)this.total / this.cycles / 1000,
                (double)this.totalInner / this.cycles / 1000,
                100 - (double)this.totalInner / this.total * 100
            ));
        }
    }
}
