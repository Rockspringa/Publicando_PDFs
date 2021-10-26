export interface Revista {
    id: number;
    editor: string;
    nombre: string;
    categoria: string;
    fechaPublicacion: Date;
    costeMes?: number;
    descripcion?: string;
    numeros?: number;
    etiquetas?: string[];
    meGustas?: number;
    
    comentariosActivos?: boolean;
    meGustasActivos?: boolean;
    suscripcionesActivas?: boolean;
}