class OwnMethodInvocation {
	private ArrayList data = new ArrayList();

	public boolean isEmpty() {
		ArrayList list = this.getData();
		return list.isEmpty();
	}

	private ArrayList getData() {
		return data;
	}
}
