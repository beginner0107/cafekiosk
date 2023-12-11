package code;

import static code.TwoSum.CHECK_ARRAY_CORRECTLY;
import static code.TwoSum.TARGET_NUMBER_POSITIVE;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TwoSumTest {

  @Test
  void twoSumTest() {
    // given
    TwoSum twoSum = new TwoSum();
    int[] nums1 = {2,7,11,15};
    int target1 = 9;

    int[] nums2 = {3,2,4};
    int target2 = 6;

    int[] nums3 = {3,3};
    int target3 = 6;

    // when & then
    assertThat(twoSum.twoSum(nums1, target1)).isEqualTo(new int[]{0,1});
    assertThat(twoSum.twoSum(nums2, target2)).isEqualTo(new int[]{1,2});
    assertThat(twoSum.twoSum(nums3, target3)).isEqualTo(new int[]{0,1});
  }

  @Test
  void twoSumArrayFailTest() {
    // given
    TwoSum twoSum = new TwoSum();
    int[] nums1 = {10};
    int target1 = 9;

    // when & then
    assertThatThrownBy(() -> twoSum.twoSum(nums1, target1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(CHECK_ARRAY_CORRECTLY);
  }

  @Test
  void twoSumTargetNumberFailTest() {
    // given
    TwoSum twoSum = new TwoSum();
    int[] nums1 = {10, 2};
    int target1 = 0;

    // when & then
    assertThatThrownBy(() -> twoSum.twoSum(nums1, target1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(TARGET_NUMBER_POSITIVE);
  }
}