package followit.Utils;

/**
 * A helper wrapper for data that contains two elements within
 * @author yigitozkavci
 *
 * @param <Fst> Type of the first data
 * @param <Snd> Type of the second data
 */
public class Tuple<Fst, Snd> {
  /**
   * First data
   */
  public Fst fst;
  
  /**
   * Second data
   */
  public Snd snd;

  public Tuple(Fst fst, Snd snd) {
    this.fst = fst;
    this.snd = snd;
  }
}
