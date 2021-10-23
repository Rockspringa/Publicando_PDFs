export interface Revista {
    id: number;
    editor: string;
    nombre: string;
    categoria: string;
    fechaPublicacion: Date;
    descripcion?: string;
    
    comentariosActivos?: boolean;
    meGustasActivos?: boolean;
    suscripcionesActivas?: boolean;
}