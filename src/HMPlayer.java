public class HMPlayer implements Player{
        @Override
        public void nextMove(Map map, int[][] positions, boolean white) {
                map.move();
        }
}
