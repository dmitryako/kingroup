package numeric.functor;
import java.util.Random;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 12:30:46 PM
 */
public class functor_rand implements Func {
  final private Random rand_;
  public functor_rand(Random rand) {
    rand_ = rand;
  }
  public double calc(double x) {
    return rand_.nextDouble();
  }
}
