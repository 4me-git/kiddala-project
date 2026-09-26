package action;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerSearchActionTest {

    private CustomerSearchAction action;

    @BeforeEach
    public void setUp() {
        action = new CustomerSearchAction();
    }

    // -------------------------------------------------------------------------
    // 正常系：各検索条件の網羅テスト
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("条件①：電話番号のみ指定（電話番号完全一致）")
    public void testExecute_TelOnly() throws Exception {
        // DBに存在する電話番号を指定（フリガナは空文字）
        String[] input = { "09012345678", "" };
        
        String[][] result = action.execute(input);

        assertNotNull(result, "検索結果がnullでないこと");
        assertTrue(result.length > 0, "1件以上の結果が取得できること");
        assertEquals("09012345678", result[0][3], "取得した顧客の電話番号が一致すること");
    }

    @Test
    @DisplayName("条件②：フリガナのみ指定（カナ部分一致）")
    public void testExecute_KanaOnly() throws Exception {
        // DBに存在するフリガナを指定（電話番号は空文字）
        String[] input = { "", "イトウ" };

        String[][] result = action.execute(input);

        assertNotNull(result, "検索結果がnullでないこと");
        assertTrue(result.length > 0, "1件以上の結果が取得できること");
        assertTrue(result[0][2].contains("イトウ"), "取得した顧客のフリガナに指定文字が含まれること");
    }

    @Test
    @DisplayName("条件③：電話番号とフリガナの両方を指定（AND検索）")
    public void testExecute_TelAndKana() throws Exception {
        // DBに存在する電話番号とフリガナを両方指定
        String[] input = { "0314142135", "ワタナベ" };

        String[][] result = action.execute(input);

        assertNotNull(result, "検索結果がnullでないこと");
        assertEquals(1, result.length, "条件に一致する1件のみ取得できること");
        assertEquals("0314142135", result[0][3], "電話番号が一致すること");
        assertTrue(result[0][2].contains("ワタナベ"), "フリガナが一致すること");
    }

    // -------------------------------------------------------------------------
    // 仕様要件：スペース除去機能のテスト
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("入力値補正：半角スペース・全角スペースが混在していても正常に除去されて検索できること")
    public void testExecute_WithSpaces() throws Exception {
        // DBに存在する実データ（伊藤華英さん: TEL="09087654321", KANA="イトウハナエ"）の条件にスペースを混ぜる
        String[] input = { " 090 8765 4321 ", " イトウ " };

        String[][] result = action.execute(input);

        assertNotNull(result, "スペース除去後に正常に検索結果が返ること");
        assertTrue(result.length > 0, "1件以上の結果が取得できること");
    }

    // -------------------------------------------------------------------------
    // 境界値・異常系：検索結果なし / 空入力
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("該当なし：存在しない条件で検索した場合、nullが返ること")
    public void testExecute_NoMatch() throws Exception {
        String[] input = { "00000000000", "" };

        String[][] result = action.execute(input);

        assertNull(result, "該当件数0件の場合、仕様どおり null が返ること");
    }

    @Test
    @DisplayName("条件なし：両方とも空文字・スペースのみの場合、nullが返ること")
    public void testExecute_EmptyInput() throws Exception {
        String[] input = { "   ", "  " };

        String[][] result = action.execute(input);

        assertNull(result, "スペース除去後に空文字となり、検索が実行されず null が返ること");
    }

    @Test
    @DisplayName("Null安全：配列要素がnullの場合でもNullPointerExceptionが発生しないこと")
    public void testExecute_NullElements() throws Exception {
        String[] input = { null, null };

        // execute内部でnullチェック（または空文字変換）がされていれば例外が出ずにnullが返る
        String[][] result = action.execute(input);

        assertNull(result, "null入力時も安全に処理され null が返ること");
    }
}