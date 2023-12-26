import React from 'react'
import SearchBar from './components/SearchBar'
import Link from 'next/link'
import type { Metadata } from 'next'

export const metadata: Metadata = {
  title: "Nerasta",
}

export default function NotFoundPage() {
  return (
    <>
      {/* <SearchBar /> */}
      <div className="r_devider"></div>
      <div className="r_loading">
        <h1>Puslapis nerastas</h1>
        <Link href="/">
          <p>Gal norite grįžti atgal?</p>
        </Link>
      </div>
    </>
    
  )
}
