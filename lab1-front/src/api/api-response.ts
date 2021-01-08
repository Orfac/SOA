export interface ApiResponse<T>{
    result : T | null,
    status : number,
    errorMessage : string | null
}

