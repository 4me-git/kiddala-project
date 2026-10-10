package test.joint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import action.CustomerSearchAction;

public class JoinTest_OrderControl03 {

    private CustomerSearchAction action;

    @BeforeEach
    public void setUp() {
        action = new CustomerSearchAction();
    }

    @Test
    @DisplayName("〔結合3-項番1〕Action経由でDBAccessから正常に検索結果が返ること")
    public void testJoin03_Hit() throws Exception {
        String[] input = { "09011112222", "イトウ" };
        String[][] result = action.execute(input);

        assertNotNull(result, "検索結果（String[][]）が取得できること");
        assertTrue(result.length > 0, "1件以上データが存在すること");
        assertEquals("09011112222", result[0][3], "電話番号が一致すること");
    }

    @Test
    @DisplayName("〔結合3-項番2〕Action経由で該当なし時にnullが返ること")
    public void testJoin03_NoHit() throws Exception {
        String[] input = { "00000000000", "ソンザイシナイ" };
        String[][] result = action.execute(input);

        assertNull(result, "該当件数0件の場合は null が返ること");
    }
}