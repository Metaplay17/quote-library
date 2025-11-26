import type { DefaultResponse } from "./types";

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

export async function makeSafeGet(url : string, navigate : (path: string) => void, showAlert : (params: {title: string, message: string}) => void): Promise<Response | null> {
  const API_URL : string = import.meta.env.VITE_API_URL;

  try {
    const response : Response = await fetch(`${API_URL}${url}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        }
    });

    if (!response.ok) {
        const json : DefaultResponse = await response.json();
        if (json.status == "UNAUTHORIZED") {
            navigate('/login');
        }
        showAlert({
            title: json.status,
            message: json.message
        });
        return null;
    }
    return response;
  } catch (ex : any) {
      showAlert({
          title: "Ошибка сети",
          message: "Проверьте подключение к интернету"
      });
      throw new Error();
  }
}

export async function makeSafePost(url : string, body: object, navigate : (path: string) => void, showAlert : (params: {title: string, message: string}) => void): Promise<Response | null> {
  const API_URL : string = import.meta.env.VITE_API_URL;

  try {
    const response : Response = await fetch(`${API_URL}${url}`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    });

    if (!response.ok) {
        const json : DefaultResponse = await response.json();
        if (json.status == "UNAUTHORIZED") {
            navigate('/login');
        }
        showAlert({
            title: json.status,
            message: json.message
        });
        return null;
    }
    return response;
  } catch (ex : any) {
      showAlert({
          title: "Ошибка сети",
          message: "Проверьте подключение к интернету"
      });
      throw new Error();
  }
}