export interface Anuncio {
    id: number,
    administrador?: string,
    fechaPublicacion?: Date,
    costoDia?: number,
    activo: boolean,
    texto: string,
    imagen?: string,
    video?: string
}