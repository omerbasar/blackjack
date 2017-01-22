package ob.games.blackjack;

/**
 * @author obasar
 */
public enum Card {

   ACE(11),
   TWO(2),
   THREE(3),
   FOUR(4),
   FIVE(5),
   SIX(6),
   SEVEN(7),
   EIGHT(8),
   NINE(9),
   TEN(10),
   JACK(10),
   QUENN(10),
   KING(10);

   private int value;

   Card(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }
}
