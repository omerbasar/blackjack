package ob.games.blackjack;

import static ob.games.blackjack.BlackjackAction.*;
import static ob.games.blackjack.Card.*;

/**
 * http://casinogambling.about.com/od/blackjack/a/bjbasic.htm
 *
 * @author obasar
 */
public class ActionSelector {

   public static BlackjackAction select(Hand hand, Card dealerUpCard) {
      if(hand.total() > 17){
         return STAND;
      }
      if (hand.isSame()) {
         switch (hand.getCard1()) {
            case ACE:
            case EIGHT:
               return SPLIT;
            case TWO:
            case THREE:
               return isBetween(hand.getCard1(), TWO, SEVEN) ? SPLIT : HIT;
            case FOUR:
               return isBetween(hand.getCard1(), FIVE, SIX) ? SPLIT : HIT;
            case FIVE:
               return isBetween(hand.getCard1(), TWO, NINE) ? SPLIT : HIT;
            case SIX:
               return isBetween(hand.getCard1(), TWO, SIX) ? SPLIT : HIT;
            case SEVEN:
               return isBetween(hand.getCard1(), TWO, SEVEN) ? SPLIT : HIT;
            case NINE:
               return isBetween(hand.getCard1(), TWO, SIX) || isBetween(hand.getCard1(), EIGHT, NINE) ? SPLIT : STAND;
            case TEN:
            case JACK:
            case QUENN:
            case KING:
               return STAND;
            default:
               return STAND;
         }
      }

      if (hand.isHard()) {
         if (hand.total() >= 17) {
            return STAND;
         } else if (hand.total() <= 8) {
            return HIT;
         }

         switch (dealerUpCard) {
            case TWO:
               return hand.total() >= 13 ? STAND : (hand.total() == 12 || hand.total() == 9 ? HIT : DOUBLE);
            case THREE:
               return hand.total() >= 13 ? STAND : (hand.total() == 12 ? HIT : DOUBLE);
            case FOUR:
            case FIVE:
            case SIX:
               return hand.total() >= 12 ? STAND : DOUBLE;
            case SEVEN:
            case EIGHT:
            case NINE:
               return hand.total() == 10 || hand.total() == 11 ? DOUBLE : HIT;
            case TEN:
            case JACK:
            case QUENN:
            case KING:
               return hand.total() == 11 ? DOUBLE : HIT;
            case ACE:
               return HIT;
            default:
               return STAND;
         }
      } else {
         if (hand.contains(TWO) || hand.contains(THREE)) {
            return isBetween(dealerUpCard, FIVE, SIX) ? DOUBLE : HIT;
         } else if (hand.contains(FOUR) || hand.contains(FIVE)) {
            return isBetween(dealerUpCard, FOUR, SIX) ? DOUBLE : HIT;
         } else if (hand.contains(SIX)) {
            return isBetween(dealerUpCard, THREE, SIX) ? DOUBLE : HIT;
         } else if (hand.contains(SEVEN)) {
            switch (dealerUpCard) {
               case TWO:
               case SEVEN:
               case EIGHT:
                  return STAND;
               case THREE:
               case FOUR:
               case FIVE:
               case SIX:
                  return DOUBLE;
               default:
                  return HIT;
            }
         } else if (hand.contains(EIGHT) || hand.contains(NINE)) {
            return STAND;
         }
      }

      return STAND;
   }

   private static boolean isBetween(Card card, Card from, Card to) {
      return card.getValue() >= from.getValue() && card.getValue() <= to.getValue();
   }
}
