package code;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

  public static final String TARGET_NUMBER_POSITIVE = "목표 값은 양수여야 합니다.";
  public static final String CHECK_ARRAY_CORRECTLY = "배열을 제대로 입력해주세요.";

  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    validationNums(nums);
    validationTargetNumber(target);
    int[] answer = new int[2];
    for (int i = 0; i < nums.length; i++) {
      int num = target - nums[i];
      if (map.containsKey(num)) {
        answer[0] = map.get(num);
        answer[1] = i;
        break;
      }
      map.put(nums[i], i);
    }
    return answer;
  }

  private static void validationTargetNumber(int target) {
    if (target <= 0) {
      throw new IllegalArgumentException(TARGET_NUMBER_POSITIVE);
    }
  }

  private static void validationNums(int[] nums) {
    if (nums.length == 0 || nums.length == 1) {
      throw new IllegalArgumentException(CHECK_ARRAY_CORRECTLY);
    }
  }
}
