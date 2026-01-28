package cardRecognitionUtil;

public final class Constants {

    public enum Colors {
        CARD((short) 255, (short) 255, (short) 255, 160);

        private final short R, G, B;
        private final int DIST;

        Colors(short R, short G, short B, int DIST) {
            this.R = R;
            this.G = G;
            this.B = B;
            this.DIST = DIST;
        }

        public short R() { return R; }
        public short G() { return G; }
        public short B() { return B; }
        public int DIST() { return DIST; }

    }

}
