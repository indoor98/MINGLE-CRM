export function formatPrice(value) {
  return value.toLocaleString("ko-KR");
}
export function toDate(beforeDate) {
  const date = new Date(beforeDate);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}
export function dateTimeToDate(dateTime) {
  return (
    dateTime.substring(0, 4) +
    "-" +
    dateTime.substring(5, 7) +
    "-" +
    dateTime.substring(8, 10)
  );
}
