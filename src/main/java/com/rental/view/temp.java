package main.java.com.rental.view;

public class temp {

	
	/**
	 * 대분류 및 소분류 카테고리 선택 후 Num2 코드 반환
	 */
	private static String selectCategory() {
		List<String[]> bigList = queryCategory("SELECT Num, Category FROM BigCategory ORDER BY Num", null);
		if (bigList.isEmpty()) {
			System.out.println("등록된 대분류 카테고리가 없습니다.");
			return null;
		}

		System.out.println("\n[대분류 카테고리]");
		for (int i = 0; i < bigList.size(); i++) {
			System.out.println(" " + (i + 1) + ". " + bigList.get(i)[1]);
		}
		System.out.print("대분류 번호를 선택하세요 >> ");
		int bigChoice = Integer.parseInt(sc.nextLine().trim());
		if (bigChoice < 1 || bigChoice > bigList.size()) {
			System.out.println("잘못된 번호입니다.");
			return null;
		}
		String selectedBigNum = bigList.get(bigChoice - 1)[0];
		String selectedBigName = bigList.get(bigChoice - 1)[1];

		List<String[]> smallList = queryCategory("SELECT Num2, Category FROM SmallCategory WHERE Num = ? ORDER BY Num2",
				selectedBigNum);
		if (smallList.isEmpty()) {
			System.out.println("등록된 소분류 카테고리가 없습니다.");
			return null;
		}

		System.out.println("\n[" + selectedBigName + " - 소분류 카테고리]");
		for (int i = 0; i < smallList.size(); i++) {
			System.out.println(" " + (i + 1) + ". " + smallList.get(i)[1]);
		}
		System.out.print("소분류 번호를 선택하세요 >> ");
		int smallChoice = Integer.parseInt(sc.nextLine().trim());
		if (smallChoice < 1 || smallChoice > smallList.size()) {
			System.out.println("잘못된 번호입니다.");
			return null;
		}

		return smallList.get(smallChoice - 1)[0];
	}

	/**
	 * 카테고리 조회 전용 DB 헬퍼 메서드
	 */
	private static List<String[]> queryCategory(String sql, String filterValue) {
		List<String[]> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			if (filterValue != null) {
				ps.setString(1, filterValue);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new String[] { rs.getString(1), rs.getString(2) });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, ps, rs);
		}
		return list;
	}

}
