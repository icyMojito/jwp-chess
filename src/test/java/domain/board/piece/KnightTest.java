package domain.board.piece;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import wooteco.chess.domain.board.Position;
import wooteco.chess.domain.board.PositionFactory;
import wooteco.chess.domain.piece.Knight;
import wooteco.chess.domain.piece.PieceColor;
import wooteco.chess.exception.NotMovableException;

public class KnightTest {

	@ParameterizedTest
	@DisplayName("목표 위치값으로 제자리가 들어오면 예외가 발생해야 함")
	@ValueSource(strings = {"a1", "b8", "f7"})
	void inputCurrentPositionThenThrowException(String input) {
		Knight knight = new Knight(PieceColor.BLACK, PositionFactory.of(input));

		Assertions.assertThatThrownBy(() -> knight.getPathTo(PositionFactory.of(input)))
			.isInstanceOf(NotMovableException.class)
			.hasMessage(String.format("현재 자리한 위치(%s)로는 이동할 수 없습니다.", input));
	}

	@ParameterizedTest
	@DisplayName("나이트가 갈 수 있는 목표 위치값이 들어오면 경로를 정상적으로 반환해야 함")
	@ValueSource(strings = {"e6", "f5", "f3", "e2", "c2", "b3", "b5", "c6"})
	void inputMovablePositionThenReturnPath(String target) {
		Knight knight = new Knight(PieceColor.BLACK, PositionFactory.of("d4"));
		List<Position> path = knight.getPathTo(PositionFactory.of(target));

		Assertions.assertThat(path.contains(PositionFactory.of(target))).isTrue();
	}

	@ParameterizedTest
	@DisplayName("나이트가 이동할 수 없는 목표 위치값이 들어오면 예외가 발생해야 함")
	@ValueSource(strings = {"b4", "c3", "c4", "c5", "d2", "d3", "d5", "d6", "e3", "e4", "e5", "f4"})
	void inputNotMovablePositionThenThrowException(String target) {
		Knight knight = new Knight(PieceColor.BLACK, PositionFactory.of("d4"));

		Assertions.assertThatThrownBy(() -> knight.getPathTo(PositionFactory.of(target)))
			.isInstanceOf(NotMovableException.class)
			.hasMessage(String.format("지정한 위치 %s는 나이트가 이동할 수 없는 곳입니다.", target));
	}
}
