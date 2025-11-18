// 1. Проверяет, что строка начинается с латинской буквы, имеет длину от 3 символов и не содержит специальных символов, кроме нижнего подчеркивания
export function isValidUsername(str: string): boolean {
  const regex = /^[a-zA-Z][a-zA-Z0-9_]*$/;
  return str.length >= 3 && regex.test(str);
}

// 2. Проверяет строку на: длину не менее 8 символов, не менее 1 цифры, не менее 1 заглавной буквы
export function isValidPassword(str: string): boolean {
  const hasMinLength = str.length >= 8;
  const hasDigit = /[0-9]/.test(str);
  const hasUpperCase = /[A-Z]/.test(str);

  return hasMinLength && hasDigit && hasUpperCase;
}