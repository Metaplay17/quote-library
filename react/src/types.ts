export default interface LoginResponse {
  status: string,
  message: string,
  username: string,
  token: string,
  privilegeLevel: number
}

export interface DefaultResponse {
  status: string,
  message: string
}

export interface Tag {
  id: number,
  name: string
}

export interface TagsListResponse {
  status: string,
  message: string,
  tags: Tag[]
}

export interface QuotesListResponse {
  status: string,
  message: string,
  quotes: Quote[]
}

export interface Quote {
  id: number,
  text: string,
  author: string,
  tags: Tag[],
  context: string
}