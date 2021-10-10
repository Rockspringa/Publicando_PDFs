export interface Revista {
    id: number;
    fecha: Date;
    editor: string;
    nombre: string;
    categoria: string;
    descripcion?: string;
}