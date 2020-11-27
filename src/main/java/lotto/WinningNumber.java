package lotto;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import lotto.lottoexception.DuplicatedNumberException;

public class WinningNumber implements Iterable<LottoNumber> {

  private final LottoNumberBundle bundle;
  private LottoNumber bonusNumber;

  private WinningNumber(LottoNumberBundle bundle) {
    this.bundle = bundle;
  }

  public static WinningNumber of(LottoNumberBundle bundle) {
    return new WinningNumber(bundle);
  }

  private void validateBonusNumberDuplication(LottoNumber lottoNumber) {
    if (this.bundle.contains(lottoNumber)) {
      throw new DuplicatedNumberException();
    }
  }

  public void addBonusNumber(LottoNumber bonusNumber) {
    validateBonusNumberDuplication(bonusNumber);
    this.bonusNumber = bonusNumber;
  }

  public LottoNumber getBonusNumber() {
    return this.bonusNumber;
  }

  @Override
  public Iterator<LottoNumber> iterator() {
    return this.bundle.iterator();
  }

  @Override
  public void forEach(Consumer<? super LottoNumber> action) {
    this.bundle.forEach(action);
  }

  @Override
  public Spliterator<LottoNumber> spliterator() {
    return this.bundle.spliterator();
  }
}