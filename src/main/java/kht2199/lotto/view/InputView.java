package kht2199.lotto.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kht2199.lotto.LottoWinningResult;
import kht2199.lotto.exception.LottoNumberDuplicatedException;
import kht2199.lotto.exception.LottoNumberNotInitiatedException;
import kht2199.lotto.exception.LottoNumberSizeInvalidException;
import kht2199.lotto.exception.input.InvalidInputError;
import kht2199.lotto.exception.input.InvalidInputException;

/**
 *
 * @author heetaek.kim
 */
public class InputView {

	private static final Scanner in = new Scanner(System.in);

	private final ResultView output;

	public InputView(ResultView output) {
		this.output = output;
	}

	public int assets() {
		String assetsString = in.nextLine();
		return Integer.parseInt(assetsString);
	}

	public void acceptWinningNumbers(LottoWinningResult winningNumbers) {
		try {
			String winningNumbersString = in.nextLine();
			validationLottoResultString(winningNumbersString);
			String[] splitNumbers = winningNumbersString.split(",");
			List<Integer> numbers = intToString(splitNumbers);
			winningNumbers.setWinningNumbers(numbers);
		} catch (InvalidInputException | LottoNumberSizeInvalidException e) {
			output.printException(e);
			acceptWinningNumbers(winningNumbers);
		} catch (NumberFormatException e) {
			output.printException(e);
			acceptWinningNumbers(winningNumbers);
		}
	}

	public void acceptBonusNumber(LottoWinningResult winningNumbers) {
		try {
			String bonusNumberString = in.nextLine();
			validationLottoResultString(bonusNumberString);
			winningNumbers.setBonusNumber(Integer.parseInt(bonusNumberString));
		} catch (InvalidInputException | LottoNumberDuplicatedException | LottoNumberNotInitiatedException e) {
			output.printException(e);
			acceptBonusNumber(winningNumbers);
		} catch (NumberFormatException e) {
			output.printException(e);
			acceptBonusNumber(winningNumbers);
		}
	}

	private List<Integer> intToString(String[] splitNumbers) throws NumberFormatException {
		List<Integer> list = new ArrayList<>();
		for (String splitNumber : splitNumbers) {
			list.add(Integer.parseInt(splitNumber));
		}
		return list;
	}

	/**
	 * 입력 문자열 자체에 대한 검증은 UI에서 검증.
	 */
	protected void validationLottoResultString(String input) throws InvalidInputException {
		if (input == null || input.equals("")) {
			throw new InvalidInputException(InvalidInputError.EMPTY);
		}
		String[] split = input.split(",");
		if (split.length != 6) {
			throw new InvalidInputException(InvalidInputError.LENGTH);
		}
		try {
			validateLottoNumber(split);
		} catch (NumberFormatException e) {
			throw new InvalidInputException(InvalidInputError.PARSING);
		}
	}

	/**
	 * 번호 유효성 체크
	 */
	protected void validateLottoNumber(String[] split)
			throws NumberFormatException, InvalidInputException {
		for (String s : split) {
			validateRange(Integer.parseInt(s));
		}
	}

	private void validateRange(int parseInt) throws InvalidInputException {
		if (parseInt < 1 || parseInt > 46) {
			throw new InvalidInputException(InvalidInputError.RANGE);
		}
	}
}
