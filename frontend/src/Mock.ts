interface IProductDetailsType {
    key: string,
    value: string,
}

export interface IProductType {
    id: number,
    name: string,
    category: string,
    code: string,
    price: number,
    details: IProductDetailsType[]
}


export const PRODUCTS: IProductType[] = [
    {
        id: 32123,
        name: "CanterBook ÜberPro",
        category: "laptops",
        code: "123",
        price: 12.13,
        details: [
            {
                key: "Cpu",
                value: "16 core, Adeona processor"
            },
            {
                key: "Display",
                value: "Yes"
            }
        ]
    },
    {
        id: 32123,
        name: "CanterBook ÜberPro",
        category: "laptops",
        code: "123",
        price: 12.13,
        details: [
            {
                key: "Cpu",
                value: "16 core, Adeona processor"
            },
            {
                key: "Display",
                value: "Yes"
            }
        ]
    },
    {
        id: 32123,
        name: "CanterBook ÜberPro",
        category: "laptops",
        code: "123",
        price: 12.13,
        details: [
            {
                key: "Cpu",
                value: "16 core, Adeona processor"
            },
            {
                key: "Display",
                value: "Yes"
            }
        ]
    },
    {
        id: 32123,
        name: "CanterBook ÜberPro",
        category: "laptops",
        code: "123",
        price: 12.13,
        details: [
            {
                key: "Cpu",
                value: "16 core, Adeona processor"
            },
            {
                key: "Display",
                value: "Yes"
            }
        ]
    }
]
