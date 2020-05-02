package domain.board.piece;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import wooteco.chess.domain.board.Position;
import wooteco.chess.domain.board.PositionFactory;
import wooteco.chess.domain.piece.PieceColor;
import wooteco.chess.domain.piece.Rook;
import wooteco.chess.exception.NotMovableException;

public class RookTest {

	@ParameterizedTest
	@DisplayName("목표 위치값으로 제자리가 들어오면 예외가 발생해야 함")
	@ValueSource(strings = {"a1", "b8", "f7"})
	void inputCurrentPositionThenThrowException(String input) {
		Rook rook = new Rook(PieceColor.BLACK, PositionFactory.of(input));

		Assertions.assertThatThrownBy(() -> rook.getPathTo(PositionFactory.of(input)))
			.isInstanceOf(NotMovableException.class)
			.hasMessage(String.format("현재 자리한 위치(%s)로는 이동할 수 없습니다.", input));
	}

	@Test
	@DisplayName("룩이 갈 수 있는 목표 위치값이 들어오면 경로를 정상적으로 반환해야 함")
	void inputMovablePositionThenReturnPath() {
		Rook rook = new Rook(PieceColor.BLACK, PositionFactory.of("d5"));
		List<Position> path = rook.getPathTo(PositionFactory.of("h5"));

		Assertions.assertThat(path.contains(PositionFactory.of("e5"))).isTrue();
		Assertions.assertThat(path.contains(PositionFactory.of("f5"))).isTrue();
		Assertions.assertThat(path.contains(PositionFactory.of("g5"))).isTrue();
		Assertions.assertThat(path.contains(PositionFactory.of("h5"))).isTrue();
	}

	@ParameterizedTest
	@DisplayName("룩이 이동할 수 없는 목표 위치값이 들어오면 예외가 발생해야 함")
	@CsvSource(value = {"d5,e6", "f3,g2", "b7,a6", "g1,f2"})
	void inputNotMovablePositionThenThrowException(String source, String target) {
		Rook rook = new Rook(PieceColor.BLACK, PositionFactory.of(source));

		Assertions.assertThatThrownBy(() -> rook.getPathTo(PositionFactory.of(target)))
			.isInstanceOf(NotMovableException.class)
			.hasMessage(String.format("지정한 위치 %s는 룩이 이동할 수 없는 곳입니다.", target));
	}
}
